
# pip install openpyxl
import openpyxl
from openpyxl.styles import Font

print(openpyxl.__version__)

wb = openpyxl.load_workbook("wb.xlsx")
sheet = wb.active

for i in range(20):
    print(sheet.cell(row=i+1, column=1).value)

wb.close()
