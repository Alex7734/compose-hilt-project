import { ExceptionFilter, Catch, ArgumentsHost, HttpException, HttpStatus } from '@nestjs/common';
import { Request, Response } from 'express';

@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
    catch(exception: HttpException, host: ArgumentsHost) {
        const ctx = host.switchToHttp();
        const response = ctx.getResponse<Response>();
        const request = ctx.getRequest<Request>();
        const status = exception.getStatus();
        const errorResponse = exception.getResponse();

        const errorMessage = typeof errorResponse === 'string'
            ? errorResponse
            : (errorResponse as any).message || exception.message;

        response.status(status).json({
            apiVersion: process.env.API_VERSION || '1.0.0',
            timestamp: new Date().toISOString(),
            status,
            error: typeof errorResponse === 'string'
                ? errorResponse
                : (errorResponse as any).error || exception.name,
            message: Array.isArray(errorMessage) ? errorMessage : [errorMessage],
            path: request.url,
        });
    }
} 