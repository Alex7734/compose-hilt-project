# Database Setup

## PostgreSQL with Docker

This project uses PostgreSQL running in a Docker container for the database. The database configuration is specified in `docker-compose.yml`.

## Starting the Database

To start the PostgreSQL container:

```bash
# Start the database container
npm run db:up
# or
yarn db:up
```

To stop the container:

```bash
# Stop the database container
npm run db:down
# or
yarn db:down
```

## Database Configuration

Database configuration is stored in the `.env` file and loaded via `src/config/database.ts`.

The default configuration expects:

```
DB_TYPE=postgres
DB_HOST=localhost
DB_PORT=5432
DB_USERNAME=admin
DB_PASSWORD=changeit
DB_DATABASE=todo_db
```

## TypeORM Setup

The application uses TypeORM for database access. The main configuration is in:

- `src/database/database.module.ts` - Database module for the application
- `src/config/typeorm.config.ts` - Configuration for TypeORM CLI commands

## Entities

Database entities are stored in the `src/entities` directory, with the main entity being `Todo`.

## Migrations

To manage database schema changes, you can use the following commands:

```bash
# Generate a migration (after making entity changes)
npm run migration:generate -- migrations/MigrationName
# or
yarn migration:generate migrations/MigrationName

# Run pending migrations
npm run migration:run
# or
yarn migration:run

# Revert the last migration
npm run migration:revert
# or
yarn migration:revert
``` 