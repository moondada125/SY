#! /usr/bin/env python

# Client and server for udp (datagram) echo.
#
# Usage: udpecho -s [port]            (to start a server)
# or:    udpecho -c host [port] <file (client)

import sys
from socket import *
import RPi.GPIO as GPIO
import time

ECHO_PORT = 50000 + 7
BUFSIZE = 1024

# For Tilt Control
tilt = 3.5
tilt2 = 3.5

GPIO.setmode(GPIO.BCM)
# LED
GPIO.setup(24, GPIO.OUT)
GPIO.output(24, False)

# Tilt Left/Right
GPIO.setup(18, GPIO.OUT)
t = GPIO.PWM(18, 50)
t.start(3.5)

 # Tilt Up/Down
GPIO.setup(23, GPIO.OUT)
p = GPIO.PWM(23, 50)
p.start(3.5)

# Curtain
GPIO.setup(19, GPIO.OUT)
GPIO.setup(26, GPIO.OUT)
GPIO.setup(13, GPIO.OUT)
pwmA = GPIO.PWM(13, 50)
pwmA.start(0)

# DoorLock
GPIO.setup(17, GPIO.OUT)
d = GPIO.PWM(17, 50)
d.start(7.5)

def main():
    if len(sys.argv) < 2:
        usage()
    if sys.argv[1] == '-s':
        server()
    elif sys.argv[1] == '-c':
        client()
    else:
        usage()

def usage():
    sys.stdout = sys.stderr
    print 'Usage: udpecho -s [port]            (server)'
    print 'or:    udpecho -c host [port] <file (client)'
    sys.exit(2)

def server():
    if len(sys.argv) > 2:
        port = eval(sys.argv[2])
    else:
        port = ECHO_PORT
    s = socket(AF_INET, SOCK_DGRAM)
    s.bind(('', port))
    print 'udp echo server ready'
    while 1:
        data, addr = s.recvfrom(BUFSIZE)
        print 'server received %r from %r' % (data, addr)
        s.sendto(data, addr)

        # Light
        if data == "Light on":
            GPIO.output(24, True)
            print "LIGHT ON !!"

        elif data == "Light off":
            GPIO.output(24, False)
            print "LIGHT OFF !!"

        # DoorLock
        elif data == "Door open":
            d.ChangeDutyCycle(2.5)
            print "DOOR OPEN !!"

        elif data == "Door close":
            d.ChangeDutyCycle(7.5)
            print "DOOR CLOSE !!"

        # Curtain
        elif data == "Curtain up":
            pwmA.ChangeDutyCycle(99)
            GPIO.output(26, True)
            GPIO.output(19, False)
            #time.sleep(10)
            print "CURTAIN UP !!"

        elif data == "Curtain down":
            pwmA.ChangeDutyCycle(99)
            GPIO.output(26, False)
            GPIO.output(19, True)
            #time.sleep(10)
            print "CURTAIN DOWN !!"

        elif data == "Curtain stop":
            GPIO.output(26, False)
            GPIO.output(19, False)
            print "CURTAIN STOP !!"

        # Tilt
        elif data == "Up":
            global tilt
            print "TILT UP !!"

            if tilt >= 12.5 :
                tilt = 12.5
            else :
                tilt = tilt + 0.5

            p.ChangeDutyCycle(tilt)

        elif data == "Down":
            global tilt
            print "TILT DOWN !!"

            if tilt <= 2.5 :
                tilt = 2.5
            else :
                tilt = tilt - 0.5

            p.ChangeDutyCycle(tilt)

        elif data == "Left":
            global tilt2
            print "TILT LEFT !!"

            if tilt2 >= 12.5 :
                tilt2 = 12.5
            else :
                tilt2 = tilt2 + 0.5

            t.ChangeDutyCycle(tilt2)

        elif data == "Right":
            global tilt2
            print "TILT RIGHT !!"

            if tilt2 <= 2.5 :
                tilt2 = 2.5
            else :
                tilt2 = tilt2 - 0.5

            t.ChangeDutyCycle(tilt2)

def client():
    if len(sys.argv) < 3:
        usage()
    host = sys.argv[2]
    if len(sys.argv) > 3:
        port = eval(sys.argv[3])
    else:
        port = ECHO_PORT
    addr = host, port
    s = socket(AF_INET, SOCK_DGRAM)
    s.bind(('', 0))
    print 'udp echo client ready, reading stdin'
    while 1:
        line = sys.stdin.readline()
        if not line:
            break
        s.sendto(line, addr)
        data, fromaddr = s.recvfrom(BUFSIZE)
        print 'client received %r from %r' % (data, fromaddr)

main()
