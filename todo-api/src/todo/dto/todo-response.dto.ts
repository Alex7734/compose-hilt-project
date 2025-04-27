import { ApiProperty } from '@nestjs/swagger';
import { Todo } from '../../entities/todo.entity';

export class TodoResponseDto {
    @ApiProperty({ description: 'Unique identifier (UUID)' })
    id: string;

    @ApiProperty({ description: 'The title of the todo' })
    title: string;

    @ApiProperty({ description: 'Description of the todo', nullable: true, type: String })
    description: string | null;

    @ApiProperty({ description: 'Completion status' })
    completed: boolean;

    @ApiProperty({ description: 'Recurring interval in milliseconds', nullable: true })
    recurringEvery: number | null;

    @ApiProperty({ description: 'When to send a notification', nullable: true })
    notificationAt: Date | null;

    @ApiProperty({ description: 'Whether the notification has been sent' })
    notified: boolean;

    @ApiProperty({ description: 'Creation timestamp' })
    createdAt: Date;

    @ApiProperty({ description: 'Last update timestamp' })
    updatedAt: Date;

    constructor(todo: Todo) {
        this.id = todo.id;
        this.title = todo.title;
        this.description = todo.description || null;
        this.completed = todo.completed;
        this.recurringEvery = todo.recurringEvery || null;
        this.notificationAt = todo.notificationAt || null;
        this.notified = todo.notified;
        this.createdAt = todo.createdAt;
        this.updatedAt = todo.updatedAt;
    }
} 