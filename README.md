# NetWorth Finance Tracker (Railway PostgreSQL + Spring Boot)

This repo contains:

- `index.html`: finance tracker UI
- `project1`: backend API with Railway PostgreSQL persistence

## Important note about Prisma + Railway Postgres

Prisma is not used in this backend.  
Automatic schema setup is implemented with Flyway migrations (no manual table creation scripts during deploy).

## UI functionality covered

- Dashboard with monthly income/expense/net balance and category spending widgets
- Add/search/filter/delete transactions with modal details
- Category management (add/delete with sub-categories)
- Asset management and net worth aggregation
- Analysis page (savings rate, charts, monthly breakdown)
- Settings (profile + app settings)
- Data import/export (JSON)

The UI syncs with backend API:

- `GET /api/state`
- `PUT /api/state`
- `DELETE /api/state`

## Railway PostgreSQL setup

1. Create a Railway project.
2. Add **PostgreSQL** service.
3. Add your backend service from this repo (`project1`).
4. In backend service variables, Railway will expose DB variables from Postgres service (`PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`, `PGPASSWORD`) when linked.

## Local run

Set env vars in PowerShell:

```powershell
$env:DATABASE_URL="jdbc:postgresql://<host>:5432/<database>?sslmode=require"
$env:DB_USER="<username>"
$env:DB_PASSWORD="<password>"
$env:CORS_ALLOWED_ORIGINS="*"
```

Start backend:

```powershell
cd project1
mvn spring-boot:run
```

Flyway runs automatically and creates `APP_STATE` table if missing.

## Deploy backend with Railway PostgreSQL

Use `project1` as Railway service (Dockerfile already present).

Required env vars:

- `SPRING_PROFILES_ACTIVE=prod`
- `CORS_ALLOWED_ORIGINS=https://<your-frontend-domain>`
- optional override: `DATABASE_URL=jdbc:postgresql://<host>:5432/<database>?sslmode=require`
- optional override: `DB_USER=<username>`
- optional override: `DB_PASSWORD=<password>`

Default behavior on Railway:

- App auto-uses `PGHOST/PGPORT/PGDATABASE/PGUSER/PGPASSWORD` if `DATABASE_URL` is not set.

## Point frontend to deployed backend

`index.html` now reads API URL from localStorage key `nw_api_base`.

Set it once in browser console:

```js
localStorage.setItem('nw_api_base', 'https://<your-railway-backend-domain>/api/state');
location.reload();
```

To switch back to local backend:

```js
localStorage.removeItem('nw_api_base');
location.reload();
```
