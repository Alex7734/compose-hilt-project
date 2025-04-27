import { Controller, Get, Post, Put, Delete, Body, Param, HttpCode, Query, ParseIntPipe } from '@nestjs/common';
import { TodoService } from './todo.service';
import { ApiTags, ApiOperation, ApiResponse, ApiParam, ApiQuery } from '@nestjs/swagger';
import { CreateTodoDto, UpdateTodoDto, TodoResponseDto } from './dto';

@ApiTags('todos')
@Controller('todos')
export class TodoController {
    constructor(private readonly todoService: TodoService) { }

    @Get()
    @ApiOperation({ summary: 'Get all todos' })
    @ApiResponse({
        status: 200,
        description: 'Return all todos',
        type: TodoResponseDto,
        isArray: true
    })
    async findAll() {
        return this.todoService.findAll();
    }

    @Get('notifications/upcoming')
    @ApiOperation({ summary: 'Get upcoming todo notifications' })
    @ApiQuery({ name: 'days', type: Number, required: false, description: 'Number of days to look ahead (default: 7)' })
    @ApiResponse({
        status: 200,
        description: 'Return upcoming todo notifications',
        type: TodoResponseDto,
        isArray: true
    })
    async findUpcomingNotifications(@Query('days', new ParseIntPipe({ optional: true })) days?: number) {
        return this.todoService.findUpcomingNotifications(days);
    }

    @Get('notifications/overdue')
    @ApiOperation({ summary: 'Get overdue todo notifications' })
    @ApiResponse({
        status: 200,
        description: 'Return overdue todo notifications',
        type: TodoResponseDto,
        isArray: true
    })
    async findOverdueNotifications() {
        return this.todoService.findOverdueNotifications();
    }

    @Get('recurring')
    @ApiOperation({ summary: 'Get recurring todos' })
    @ApiResponse({
        status: 200,
        description: 'Return recurring todos',
        type: TodoResponseDto,
        isArray: true
    })
    async findRecurringTodos() {
        return this.todoService.findRecurringTodos();
    }

    @Get(':id')
    @ApiOperation({ summary: 'Get a todo by id' })
    @ApiParam({ name: 'id', description: 'Todo UUID' })
    @ApiResponse({
        status: 200,
        description: 'Return the todo',
        type: TodoResponseDto
    })
    @ApiResponse({ status: 404, description: 'Todo not found' })
    async findOne(@Param('id') id: string) {
        return this.todoService.findOne(id);
    }

    @Post()
    @ApiOperation({ summary: 'Create a new todo' })
    @ApiResponse({
        status: 201,
        description: 'Todo has been created',
        type: TodoResponseDto
    })
    @ApiResponse({ status: 400, description: 'Bad request (validation error)' })
    async create(@Body() createTodoDto: CreateTodoDto) {
        return this.todoService.create(createTodoDto);
    }

    @Put(':id')
    @ApiOperation({ summary: 'Update a todo' })
    @ApiParam({ name: 'id', description: 'Todo UUID' })
    @ApiResponse({
        status: 200,
        description: 'Todo has been updated',
        type: TodoResponseDto
    })
    @ApiResponse({ status: 404, description: 'Todo not found' })
    @ApiResponse({ status: 400, description: 'Bad request (validation error)' })
    async update(
        @Param('id') id: string,
        @Body() updateTodoDto: UpdateTodoDto,
    ) {
        return this.todoService.update(id, updateTodoDto);
    }

    @Delete(':id')
    @HttpCode(204)
    @ApiOperation({ summary: 'Delete a todo' })
    @ApiParam({ name: 'id', description: 'Todo UUID' })
    @ApiResponse({ status: 204, description: 'Todo has been deleted' })
    @ApiResponse({ status: 404, description: 'Todo not found' })
    async remove(@Param('id') id: string): Promise<void> {
        await this.todoService.remove(id);
    }
} 