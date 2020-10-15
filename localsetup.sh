docker run --name neo4j -p7474:7474 -p7687:7687 -d -v $HOME/neo4j/data:/data -v $HOME/neo4j/logs:/logs -v $HOME/neo4j/import:/var/lib/neo4j/import -v $HOME/neo4j/plugins:/plugins --env NEO4J_AUTH=neo4j/pass neo4j:4.1.3

sleep 5

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

clear

echo "Setup is done. Open Neo4j in browser http://0.0.0.0:7474/browser/"
