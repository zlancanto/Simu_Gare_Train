
printf "\n*** Trains ***\n"

curl -X GET http://localhost:8124/trains
printf "\n*** Trains ***\n"

curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' -d '{"nbPlaces": "30"}' http://localhost:8124/trains
printf "\n*** Trains ***\n"

curl -X GET http://localhost:8124/trains


printf "\n*** Voyageurs ***\n"

curl -X GET http://localhost:8124/voyageurs
printf "\n*** Voyageurs ***\n"


curl -X POST -H 'Content-type:application/json' -H 'Accept:application/json' http://localhost:8124/voyageurs


printf "\n*** Voyageurs ***\n"

