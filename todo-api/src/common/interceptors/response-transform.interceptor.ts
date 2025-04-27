import { Injectable, NestInterceptor, ExecutionContext, CallHandler } from '@nestjs/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class ResponseTransformInterceptor implements NestInterceptor {
    intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
        const response = context.switchToHttp().getResponse();
        if (response.statusCode === 204) {
            return next.handle();
        }

        return next.handle().pipe(
            map(data => {
                if (data && data.apiVersion && data.timestamp && data.data) {
                    return data;
                }

                return {
                    apiVersion: process.env.API_VERSION || '1.0.0',
                    timestamp: new Date().toISOString(),
                    data
                };
            }),
        );
    }
} 