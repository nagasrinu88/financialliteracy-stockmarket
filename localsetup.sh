# pulling neo4j image
docker pull neo4j:4.1.3

# pulling postgres image
docker pull postgres:13.0


# running neo4j docker container
docker run --name neo4j -p7474:7474 -p7687:7687 -d -v $HOME/neo4j/data:/data -v $HOME/neo4j/logs:/logs -v $HOME/neo4j/import:/var/lib/neo4j/import -v $HOME/neo4j/plugins:/plugins --env NEO4J_AUTH=neo4j/pass neo4j:4.1.3

sleep 10

# running postgre container
docker run --name postgresDB -d -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:13.0

sleep 10

# neo4j testdata
docker exec -it neo4j cypher-shell "MATCH (n) DETACH DELETE n;" -u neo4j -p pass

docker exec -it neo4j cypher-shell "
CREATE (Mahesh:User{userName:'mahesh', fullName: 'Mahesh'})
CREATE (Naga:User{userName:'naga', fullName: 'Naga'}) 
CREATE (Nikitha:User{userName:'nikitha', fullName:'Nikitha'})
CREATE (Jagruthi: User{userName: 'jagruthi', fullName: 'Jagruthi'})
CREATE (Anief: User{userName: 'anief', fullName: 'Anief'})
CREATE (Reliance: Company {name: 'Reliance', symbol: 'Reliance', exchange: 'NSE', mCapInLakh: 706035.7930725, cap: 'LARGE_CAP'})
CREATE (TCS: Company {name: 'Tata Consultancy Services Limited', symbol: 'TCS', exchange: 'NSE', mCapInLakh: 685222.97116266, cap: 'LARGE_CAP'})
CREATE (Reliance)<-[:WATCHLIST]-(Mahesh)
CREATE (Reliance)<-[:WATCHLIST]-(Naga)
CREATE (Reliance)<-[:WATCHLIST]-(Anief)
CREATE (Reliance)<-[:WATCHLIST]-(Jagruthi)
CREATE (TCS)<-[:WATCHLIST]-(Mahesh)
CREATE (TCS)<-[:WATCHLIST]-(Nikitha)
CREATE (TCS)<-[:WATCHLIST]-(Jagruthi)
" -u neo4j -p pass


# postgres tables
set -e

POSTGRES_SQL="
create table if not exists DAILY_SECURITY_PRICE  (
ID serial primary key,
SYMBOL varchar(50),
SERIES varchar(10),
DATE1  timestamp,
PREV_CLOSE numeric(10,2),
OPEN_PRICE numeric(10,2),
HIGH_PRICE numeric(10,2),
LOW_PRICE numeric(10,2),
LAST_PRICE numeric(10,2),
CLOSE_PRICE	numeric(10,2),
AVG_PRICE numeric(10,2),
TTL_TRD_QNTY integer,
DELIV_QTY integer,
DELIV_PER numeric(6,2)
);

CREATE table if not exists public.LISTED_SECURITY (
	SYMBOL varchar(50) NULL,
	COMPANY_NAME varchar(200) null,
	SERIES varchar(10) NULL,
	EXCHANGE varchar(3) null,
	CONSTRAINT LISTED_SECURITIES_PKEY PRIMARY KEY (SYMBOL)
);

"
docker exec -it postgresDB sh -c "psql -U postgres -d postgres -c \"$POSTGRES_SQL\" ";

clear

echo "Setup is done. Open Neo4j in browser http://0.0.0.0:7474/browser/"
