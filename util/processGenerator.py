from random import randint
with open ("processos.csv", "a") as f:
	for i in range(10):
		pid = i + 1
		arrivalTime = randint(1,15)
		burstTime = randint(1,6)
		priority = randint(1,30)
		f.write(str(arrivalTime) + "," + str(pid) + "," + str(burstTime) + "," + str(priority) + "\n") 