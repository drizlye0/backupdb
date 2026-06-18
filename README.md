# backupdb
backupdb is a cli tool for backup database schemas

# Usage

``` shell
$ backupd --help
cli utility for create backups for your database schemas
Usage: backupdb [-help] [-version] [-db=<dbName>] [-host=<host>]
                [-pass=<password>] [-port=<port>] [-prov=<prov>]
                [-user=<username>] <path>
      <path>                         path of result files
      -db, --database=<dbName>       database name
      -help, --help                  display help message
      -host, --host=<host>           database host
      -pass, --password=<password>   database password
      -port, --port=<port>           database port
      -prov, --provider=<prov>       database manager provider
      -user, --username=<username>   database username
      -version, --version            display version info
```

Example:

``` shell
$ backupdb -host=localhost -port=3306 -user=dbuser -pass=dbpassword -prov=mysql -db=backupdb backups/
```

The results sql files will be saved inside `backups/` folder if exists

``` shell
$ tree backups
backups/
└── tables
    ├── orders.sql
    ├── users2.sql
    └── users.sql
```

