# kogito-grafana
PoC update grafana panels at runtime based on the fired rules

Run it with 
```
sudo docker-compose build && sudo docker-compose up
```

And visit `localhost:3000` to check out the grafana dashboard. 

After that run 

```
curl -X POST http://localhost:8080/persons     -H 'content-type: application/json'     -H 'accept: application/json'     -d '{"person": {"name":"John Quark", "age": 10}}'
```

Check out `localhost:3000` again, a new panel for the rule `Is not adult` has been created. 

Run 

```
curl -X POST http://localhost:8080/persons     -H 'content-type: application/json'     -H 'accept: application/json'     -d '{"person": {"name":"John Quark", "age": 30}}'
```

And a new panel will be created for the rule `Is adult`.
