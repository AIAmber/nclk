import MySQLdb
import re
import os
import time
import random
import datetime

databaseName = 'NCLK'
username = 'NCLK'
password = 'NCLK'
host = '192.168.1.147'
port = 3306

# conn = MySQLdb.connect(host=host, port=port, user=username, passwd=password, db=databaseName)
# cur = conn.cursor()

# cur.execute("CREATE TABLE IF NOT EXISTS %s (%s)" %(tableName, sqlText))
# dbNum = cur.execute("SHOW DATABASES")
# conn.commit()
# print("conn success")
# print(dbNum)
ip3 = 8
ip4 = 1
dt=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S") # timestamp
tne_id = 220000100300
tne_ip = '192.168.' + str(ip3) + '.' + str(ip4)
tne_name = 'NanjingUST, Science 1-1, Room503'
endpointId = 2200001009
createDate = dt
state = ''

def saveData():
	rdPeed = random.randint(1,10) # state (random)
	if 1 == rdPeed % 2:
		state = 'error'
	if 0 == rdPeed % 2:
		state = 'normal'
	# state = 'normal'

	conn = MySQLdb.connect(host=host, port=port, user=username, passwd=password, db=databaseName)
	cur = conn.cursor()

	cur.execute("INSERT INTO T_NCLK_EXAMROOM (EXAMROOM_ID, EXAMROOM_IP, EXAMROOM_NAME, ENDPOINT_ID, CREATE_DATE, STATE)\
				VALUES ('%s', '%s', '%s', '%d', '%s', '%s')" %(str(tne_id), tne_ip, tne_name, endpointId, createDate, state))
	# cur.execute("SELECT * FROM `NCLK`.`T_NCLK_EXAMROOM`")
	conn.commit()
	cur.close()
	conn.close()

j = 27
while j>0:
	j = j-1
	i = 50
	endpointId = endpointId+1 # endpoint++
	ip3 = ip3+1 # the 3rd ip++
	ip4 = 1
	while i>0:
		i = i-1
		tne_id = tne_id+1 # examroom ++
		ip4 = ip4+1 # the 4th ip++
		tne_ip = '192.168.' + str(ip3) + '.' + str(ip4)
		saveData()
		print("There are "+ str(i) + " data leaving!")
		time.sleep(0.7)


exit()
