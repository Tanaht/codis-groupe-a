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

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)

channel.basic_consume(callback,
                    queue='hello',
                    no_ack=True)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()

