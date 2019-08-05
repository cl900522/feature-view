import matplotlib
import numpy as np
import matplotlib.pyplot as plt

num_bins  = 30

mu = 100  # mean of distribution
sigma = 15  # standard deviation of distribution
x = np.random.randn(437)
print(x.size)
x = mu + sigma * x
plt.subplots()
n, bins, patches = plt.hist(x, num_bins, density=1)

print(len(bins))

y = ((1 / (np.sqrt(2 * np.pi) * sigma)) *
     np.exp(-0.5 * (1 / sigma * (bins - mu))**2))


plt.tight_layout()
plt.plot(bins, y, '--')
plt.show()
