import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { ValidationPipe } from '@nestjs/common';
import { HttpExceptionFilter } from './common/filters/http-exception.filter';
import { TodoResponseDto } from './todo/dto';
import { ApiResponseWrapperDto } from './common/dto/api-response.dto';
import { ResponseTransformInterceptor } from './common/interceptors/response-transform.interceptor';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.setGlobalPrefix('api');

  app.enableCors();

  app.useGlobalPipes(
    new ValidationPipe({
      whitelist: true,
      forbidNonWhitelisted: true,
      transform: true,
      transformOptions: {
        enableImplicitConversion: true,
      },
    }),
  );

  app.useGlobalFilters(new HttpExceptionFilter());

  app.useGlobalInterceptors(new ResponseTransformInterceptor());

  const config = new DocumentBuilder()
    .setTitle(process.env.SWAGGER_DOC_TITLE || 'Todo API')
    .setDescription(process.env.SWAGGER_DOC_DESCRIPTION || 'API for Todo application')
    .setVersion(process.env.SWAGGER_DOC_VERSION || '1.0')
    .addTag('todos')
    .build();

  const document = SwaggerModule.createDocument(app, config, {
    extraModels: [ApiResponseWrapperDto, TodoResponseDto],
  });
  SwaggerModule.setup('docs', app, document);

  await app.listen(process.env.PORT ?? 3000);
  console.log(`Application is running on: ${await app.getUrl()}`);
}
bootstrap();