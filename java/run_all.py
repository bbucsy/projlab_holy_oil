import os

os.system('compile.bat')

for filename in os.listdir('testdir'):
    file = os.path.join('testdir', filename)
    if (os.path.isfile(file)):
        # print(str(filename))
        if (str(filename).startswith('test')):
            #print("COMMAND:", 'run.bat < ./' + str(file)+ " > " + os.path.join('testdir', str(filename).replace('test', 'auto_res')))
            os.system('run.bat < .\\' + str(file)+ " > " + os.path.join('testdir', str(filename).replace('test', 'auto_res')))
