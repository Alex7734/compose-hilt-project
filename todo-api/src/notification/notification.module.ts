import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ScheduleModule } from '@nestjs/schedule';
import { NotificationService } from './notification.service';
import { Todo } from '../entities/todo.entity';

@Module({
    imports: [
        TypeOrmModule.forFeature([Todo]),
        ScheduleModule.forRoot(),
    ],
    providers: [NotificationService],
    exports: [NotificationService],
})
export class NotificationModule { } 