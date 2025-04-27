import { DataSource } from 'typeorm';
import { config } from 'dotenv';
import databaseConfig from './database';

config();

const dbConfig = databaseConfig();

export default new DataSource({
    type: dbConfig.type as any,
    host: dbConfig.host,
    port: dbConfig.port,
    username: dbConfig.username,
    password: dbConfig.password,
    database: dbConfig.database,
    entities: [__dirname + '/../**/*.entity{.ts,.js}'],
    migrations: [__dirname + '/../migrations/**/*{.ts,.js}'],
    synchronize: false,
});

module.exports = new DataSource({
    type: dbConfig.type as any,
    host: dbConfig.host,
    port: dbConfig.port,
    username: dbConfig.username,
    password: dbConfig.password,
    database: dbConfig.database,
    entities: [__dirname + '/../**/*.entity{.ts,.js}'],
    migrations: [__dirname + '/../migrations/**/*{.ts,.js}'],
    synchronize: false,
}); 