import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { AppController } from './app.controller';
import { DatabaseModule } from './database/database.module';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Todo } from './entities/todo.entity';
import databaseConfig from './config/database';
import appConfig from './config/appConfig';
import { TodoModule } from './todo/todo.module';
import { NotificationModule } from './notification/notification.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
      load: [databaseConfig, appConfig],
    }),
    DatabaseModule,
    TypeOrmModule.forFeature([Todo]),
    TodoModule,
    NotificationModule,
  ],
  controllers: [AppController],
  providers: [],
})
export class AppModule { }
