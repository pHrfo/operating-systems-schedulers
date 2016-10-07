from random import randint
with open ("processos.csv", "a") as f:
	for i in range(100):
		pid = i + 1
		arrivalTime = randint(1,100)
		burstTime = randint(1,30)
		f.write(str(arrivalTime) + "," + str(pid) + "," + str(burstTime) + ",0\n") 