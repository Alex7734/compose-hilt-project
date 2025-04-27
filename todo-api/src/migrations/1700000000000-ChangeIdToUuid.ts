import { MigrationInterface, QueryRunner } from "typeorm";

export class ChangeIdToUuid1700000000000 implements MigrationInterface {
    name = 'ChangeIdToUuid1700000000000'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`TRUNCATE TABLE "todo" RESTART IDENTITY CASCADE`);

        await queryRunner.query(`ALTER TABLE "todo" DROP CONSTRAINT "PK_d429b7114371f6a35c5cb4776a7"`);

        await queryRunner.query(`ALTER TABLE "todo" DROP COLUMN "id"`);
        await queryRunner.query(`ALTER TABLE "todo" ADD "id" uuid NOT NULL DEFAULT uuid_generate_v4()`);

        await queryRunner.query(`CREATE EXTENSION IF NOT EXISTS "uuid-ossp"`);

        await queryRunner.query(`ALTER TABLE "todo" ADD CONSTRAINT "PK_d429b7114371f6a35c5cb4776a7" PRIMARY KEY ("id")`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "todo" DROP CONSTRAINT "PK_d429b7114371f6a35c5cb4776a7"`);

        await queryRunner.query(`ALTER TABLE "todo" DROP COLUMN "id"`);
        await queryRunner.query(`ALTER TABLE "todo" ADD "id" SERIAL NOT NULL`);

        await queryRunner.query(`ALTER TABLE "todo" ADD CONSTRAINT "PK_d429b7114371f6a35c5cb4776a7" PRIMARY KEY ("id")`);
    }
} 