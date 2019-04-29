import os

dataPath = "data/"
wordsFile = "words.txt"

dateStr = str(raw_input("Enter date in yyyy-mm-dd formart to filter :"))
finalFile = dateStr + "-final.txt"

words = set()

def file_to_set(fileName):
	data = set()
	fp = open(fileName, "r")
	text = fp.read()
	fp.close()
	for w in text.split("\n"):
		if len(w.strip()) > 0:
			data.add(w.strip())
	return data

def all_substring(word):
	length = len(word)
	return [word[i:j+1] for i in xrange(length) for j in xrange(i,length)]

# read common words
words = file_to_set(wordsFile)
print("Loaded words set with size " + str(len(words)))

files = []

# find file to process
for r, d, f in os.walk(dataPath):
    for file in f:
        if dateStr in file:
            files.append(os.path.join(r, file))

#open file file for writing
fp1 = open(finalFile, "a")
finalDomains = set()

for f in files:
	print("Processing file " + str(f))
	domains = file_to_set(f)
	for d in domains:
		name = d.split(".")[0]
		if name.isalpha():
			print("Processing domain : " + str(name))
			subStrings = all_substring(name)
			for s in subStrings:
				if len(s) > 2 and s in words:
					finalDomains.add(d)

for d in finalDomains:
	fp1.write(d + "\n")
fp1.close()