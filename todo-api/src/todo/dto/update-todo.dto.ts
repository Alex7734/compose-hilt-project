import { IsString, IsOptional, IsBoolean, IsNumber, IsISO8601 } from 'class-validator';
import { ApiPropertyOptional } from '@nestjs/swagger';
import { Type } from 'class-transformer';

export class UpdateTodoDto {
    @ApiPropertyOptional({ description: 'The title of the todo item' })
    @IsString()
    @IsOptional()
    title?: string;

    @ApiPropertyOptional({ description: 'Optional description of the todo item' })
    @IsString()
    @IsOptional()
    description?: string;

    @ApiPropertyOptional({ description: 'Indicates if the todo is completed' })
    @IsBoolean()
    @IsOptional()
    completed?: boolean;

    @ApiPropertyOptional({
        description: 'Recurring interval in milliseconds. If set, the task will repeat at this interval.',
        example: 86400000
    })
    @IsNumber()
    @IsOptional()
    recurringEvery?: number;

    @ApiPropertyOptional({
        description: 'When to send a notification for this todo item (ISO8601 format)',
        example: '2023-05-30T14:00:00Z'
    })
    @IsISO8601()
    @Type(() => Date)
    @IsOptional()
    notificationAt?: Date;
} 