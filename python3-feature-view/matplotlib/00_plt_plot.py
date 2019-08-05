import matplotlib
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.pyplot import Axes

# Data for plotting
t = np.arange(0.0, 2.0, 0.01)
s = 1 + np.sin(2 * np.pi * t)
c = 1 + np.cos(2 * np.pi * t)

ax:Axes = None

fig, ax = plt.subplots()
ax.plot(t, s)
ax.set(xlabel='time (s)', ylabel='voltage (mV)',
       title='About as simple as it gets, folks')
ax.set_xlabel("Time(s)")

ax.grid()
#fig.save("t.png")
plt.show()


'''
plt.subplots()
plt.plot(t, s, 'ob')
plt.plot(t, c, '-.k')
plt.xlabel('time (s)')
plt.ylabel('voltage (mV)')
plt.title('About as simple as it gets, folks')
plt.grid()
plt.show()
'''