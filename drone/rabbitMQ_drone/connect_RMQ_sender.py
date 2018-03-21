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

# Dans RabbitMQ, un message ne peut jamais être envoyé directement à la queue, il doit toujours passer par "exchange"
# Specifier le nom de la queue de destination dans "exchange" dans le parametre "routing_key"
channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')
print(" [x] Sent 'Hello World!'")

# Message envoye et connexion fermee
connection.close()

