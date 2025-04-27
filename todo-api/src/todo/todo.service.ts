import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Between, LessThanOrEqual, MoreThan, Repository } from 'typeorm';
import { Todo } from '../entities/todo.entity';
import { CreateTodoDto, UpdateTodoDto, TodoResponseDto } from './dto';

@Injectable()
export class TodoService {
    constructor(
        @InjectRepository(Todo)
        private todoRepository: Repository<Todo>,
    ) { }

    async findAll(): Promise<TodoResponseDto[]> {
        const todos = await this.todoRepository.find();
        return todos.map(todo => new TodoResponseDto(todo));
    }

    async findOne(id: string): Promise<TodoResponseDto> {
        const todo = await this.todoRepository.findOneBy({ id });
        if (!todo) {
            throw new NotFoundException(`Todo with ID ${id} not found`);
        }
        return new TodoResponseDto(todo);
    }

    async create(createTodoDto: CreateTodoDto): Promise<TodoResponseDto> {
        const todo = this.todoRepository.create({
            ...createTodoDto,
            notified: false
        });
        const savedTodo = await this.todoRepository.save(todo);
        return new TodoResponseDto(savedTodo);
    }

    async update(id: string, updateTodoDto: UpdateTodoDto): Promise<TodoResponseDto> {
        await this.findOne(id);

        const originalTodo = await this.todoRepository.findOneBy({ id });

        if (updateTodoDto.notificationAt &&
            originalTodo?.notificationAt?.getTime() !== new Date(updateTodoDto.notificationAt).getTime()) {
            updateTodoDto = {
                ...updateTodoDto,
                notified: false
            } as UpdateTodoDto & { notified: boolean };
        }

        await this.todoRepository.update(id, updateTodoDto);

        const updated = await this.todoRepository.findOneBy({ id });
        return new TodoResponseDto(updated!);
    }

    async remove(id: string): Promise<void> {
        await this.findOne(id);

        await this.todoRepository.delete(id);
    }

    async findUpcomingNotifications(days: number = 7): Promise<TodoResponseDto[]> {
        const now = new Date();
        const endDate = new Date();
        endDate.setDate(now.getDate() + days);

        const todos = await this.todoRepository.find({
            where: {
                notificationAt: Between(now, endDate),
                completed: false
            },
            order: {
                notificationAt: 'ASC'
            }
        });

        return todos.map(todo => new TodoResponseDto(todo));
    }

    async findOverdueNotifications(): Promise<TodoResponseDto[]> {
        const now = new Date();

        const todos = await this.todoRepository.find({
            where: {
                notificationAt: LessThanOrEqual(now),
                completed: false
            },
            order: {
                notificationAt: 'ASC'
            }
        });

        return todos.map(todo => new TodoResponseDto(todo));
    }

    async findRecurringTodos(): Promise<TodoResponseDto[]> {
        const todos = await this.todoRepository.find({
            where: {
                recurringEvery: MoreThan(0)
            },
            order: {
                notificationAt: 'ASC'
            }
        });

        return todos.map(todo => new TodoResponseDto(todo));
    }
} 