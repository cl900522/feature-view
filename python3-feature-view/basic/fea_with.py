class T:
    def __enter__(self):
        print("enter")

    def __exit__(self, exc_type, exc_value, traceback):
        print(exc_type, exc_value, traceback)
        print("exit")


with T() as t:
    print("process")
