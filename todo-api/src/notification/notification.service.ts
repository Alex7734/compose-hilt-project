import { Injectable, Logger } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { LessThanOrEqual, Repository, Not, IsNull, And } from 'typeorm';
import { Cron, CronExpression } from '@nestjs/schedule';
import { Todo } from '../entities/todo.entity';

@Injectable()
export class NotificationService {
    private readonly logger = new Logger(NotificationService.name);

    constructor(
        @InjectRepository(Todo)
        private todoRepository: Repository<Todo>,
    ) { }

    /**
     * Process notifications that are due
     * Runs every minute
     */
    @Cron(CronExpression.EVERY_MINUTE)
    async handleNotifications() {
        this.logger.debug('Checking for due notifications...');

        const now = new Date();
        const todosToNotify = await this.todoRepository.find({
            where: {
                notificationAt: And(LessThanOrEqual(now), Not(IsNull())),
                notified: false,
                completed: false
            },
        });

        if (todosToNotify.length === 0) {
            return;
        }

        this.logger.log(`Processing ${todosToNotify.length} notifications`);

        for (const todo of todosToNotify) {
            try {
                this.logger.log(`Sending notification for todo: ${todo.id} - ${todo.title}`);

                todo.notified = true;

                if (todo.recurringEvery) {
                    const nextNotificationTime = new Date(
                        todo.notificationAt.getTime() + todo.recurringEvery
                    );
                    todo.notificationAt = nextNotificationTime;
                    todo.notified = false;

                    this.logger.log(
                        `Rescheduled recurring todo ${todo.id} for ${nextNotificationTime.toISOString()}`
                    );
                }

                await this.todoRepository.save(todo);
            } catch (error) {
                this.logger.error(`Failed to process notification for todo ${todo.id}`, error);
            }
        }
    }

    /**
     * Reset notification status for completed todos that are marked as not notified
     * Runs every hour
     */
    @Cron(CronExpression.EVERY_HOUR)
    async cleanupNotifications() {
        this.logger.debug('Cleaning up notification status...');

        const completedTodos = await this.todoRepository.find({
            where: {
                completed: true,
                notified: false,
                notificationAt: Not(IsNull())
            },
        });

        if (completedTodos.length === 0) {
            return;
        }

        this.logger.log(`Cleaning up ${completedTodos.length} notifications for completed todos`);

        for (const todo of completedTodos) {
            todo.notified = true;
            await this.todoRepository.save(todo);
        }
    }
} 