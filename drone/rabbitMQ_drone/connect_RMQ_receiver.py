#!/usr/bin/env python
# coding=utf-8
import pika

# Etablir une connexion avec le serveur RabbitMQ :
# host  "lapommevolante.istic.univ-rennes1.fr" and  port 5672
# username "admin" and password "admin"
credentials = pika.PlainCredentials('admin', 'admin')
parameters = pika.ConnectionParameters('lapommevolante.istic.univ-rennes1.fr',
                                       8081,
                                       '/',
                                       credentials)

connection = pika.BlockingConnection(parameters)
channel = connection.channel()

# Creons une queue "hello" à laquelle le message sera livré:
channel.queue_declare(queue='hello')

# Une fonction "callback" souscrit a une "queue", lors d'une reception de message elle est appelée
def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)
# Indiquer à RabbitMQ que cette fonction "callback" doit recevoir des messages de la "queue" hello
channel.basic_consume(callback,
                    queue='hello',
                    no_ack=True)

# Une boucle sans fin qui attend des données et exécute des rappels si nécessaire.
print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()

