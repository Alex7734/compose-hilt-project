import { MigrationInterface, QueryRunner } from "typeorm";

export class AddNotificationFields1700000000001 implements MigrationInterface {
    name = 'AddNotificationFields1700000000001'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "todo" ADD "recurring_every" integer`);
        await queryRunner.query(`ALTER TABLE "todo" ADD "notification_at" TIMESTAMP`);
        await queryRunner.query(`ALTER TABLE "todo" ADD "notified" boolean NOT NULL DEFAULT false`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE "todo" DROP COLUMN "notified"`);
        await queryRunner.query(`ALTER TABLE "todo" DROP COLUMN "notification_at"`);
        await queryRunner.query(`ALTER TABLE "todo" DROP COLUMN "recurring_every"`);
    }
} 