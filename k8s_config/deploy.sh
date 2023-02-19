kubectl create configmap init-script --from-file=01-schema.sql --from-file=02-dataload.sql
kubectl apply -f jpetstore-configmap.yaml
kubectl apply -f jpetstore-secret.yaml
kubectl apply -f jpetstore-mysql-pv.yaml
kubectl apply -f jpetstore-database.yaml
kubectl apply -f jpetstore-main.yaml
