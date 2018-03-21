#!/usr/bin/env python
import pika

# Set the connection parameters to connect to lapommevolante.istic.univ-rennes1.fr on port 5672
# on the / virtual host using the username "admin" and password "admin"
credentials = pika.PlainCredentials('admin', 'admin')
parameters = pika.ConnectionParameters('lapommevolante.istic.univ-rennes1.fr',
                                       8081,
                                       '/',
                                       credentials)
                                       
connection = pika.BlockingConnection(parameters)
channel = connection.channel()


channel.queue_declare(queue='hello')

channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')
print(" [x] Sent 'Hello World!'")
connection.close()