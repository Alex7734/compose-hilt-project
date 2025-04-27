import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule, ConfigService } from '@nestjs/config';
import databaseConfig from '../config/database';

@Module({
    imports: [
        TypeOrmModule.forRootAsync({
            imports: [ConfigModule.forFeature(databaseConfig)],
            inject: [ConfigService],
            useFactory: (configService: ConfigService) => {
                const dbConfig = configService.get('database');
                return {
                    type: dbConfig.type,
                    host: dbConfig.host,
                    port: dbConfig.port,
                    username: dbConfig.username,
                    password: dbConfig.password,
                    database: dbConfig.database,
                    entities: [__dirname + '/../**/*.entity{.ts,.js}'],
                    synchronize: process.env.NODE_ENV !== 'production',
                };
            },
        }),
    ],
})
export class DatabaseModule { } 