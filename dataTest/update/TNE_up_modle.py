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

dt=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S") # timestamp
tne_id = 220000100101
tne_ip = '192.168.3.1'
tne_name = 'NanjingUST, Science 1-1, Room503'
endpointId = 2200001001
createDate = dt
state = ''

# def updateData(cur):

# 	# cur.execute("SELECT * FROM `NCLK`.`T_NCLK_EXAMROOM`")
	
j = 100
while j>1:
	j = j-1
	i = 100
	tne_id = 220000100101
	# try:
	# 	conn = MySQLdb.connect(host=host, port=port, user=username, passwd=password, db=databaseName)
	# 	cur = conn.cursor()
	# 	print("Link successfully!")
	# except Exception as e:
	# 	print("Link failed!!!!!!!")
	
	while i>1:
		i = i-1
		tne_id = tne_id+1
		# updateData(cur)
		rdPeed = random.randint(1,1000) # state (random)
		if 1 == rdPeed % 2:
			state = 'error'
		if 0 == rdPeed % 2:
			state = 'normal'

		try:
			conn = MySQLdb.connect(host=host, port=port, user=username, passwd=password, db=databaseName)
			cur = conn.cursor()
			print("Link successfully!")
		except Exception as e:
			print("Link failed!!!!!!!")

		try:
			cur.execute("UPDATE T_NCLK_EXAMROOM SET EXAMROOM_IP = '%s', EXAMROOM_NAME = '%s', \
				ENDPOINT_ID = '%d', CREATE_DATE = '%s', STATE = '%s' WHERE EXAMROOM_ID = '%s'"\
				%(tne_ip, tne_name, endpointId, createDate, state, str(tne_id)))
			conn.commit()
			print("Update the "+ str(i) + " data successfully!")
		except Exception as e:
			print("Update the "+ str(i) + " data failed!!!!!!!")
			# raise e
		
		
	try:
		cur.close()
		conn.close()
		print("Close successfully!")
	except Exception as e:
		print("Close failed!!!!!!!")
	
	

	time.sleep(1.4)

exit()
