#!python
#!/usr/bin/python3

while True:
    try:
        x = int(input("Please enter a number: "))
    except ValueError:
        print("Oops!  That was no valid number.  Try again")
    else:
        print("Exit, bye!")
        break


class MyError(Exception):
    def __init__(self, value):
        self.value = value

    def __str__(self):
        return repr(self.value)


try:
    raise MyError("Throw an Error")
except MyError as e:
    print(e)
finally:
    print('Cleaning it')
