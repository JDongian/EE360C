import plotly
from plotly.graph_objs import Scatter, Layout


data = ([0.154,
 0.165,
 0.161,
 0.239,
 0.157,
 5.008,
 1.059],
[0.202,
 0.206,
 0.202,
 0.185,
 0.206,
 0.208,
 0.215,
 0.212])

data_brute, data_gs = data

x_brute = list(range(4, 4 + len(data_brute)))
x_gs = list(range(4, 4 + len(data_gs)))

plot_data = {
"data": [
    Scatter(x=x_brute, y=data_brute, name="Brute Force"),
    Scatter(x=x_gs, y=data_gs, name="Gale-Shapley")
    ],
"layout": Layout(
    title="Brute Force vs. Gale-Shapley",
    xaxis=dict(
        title='group size (n)',
        titlefont=dict(
            family='Courier New, monospace',
            size=18,
            color='#7f7f7f'
            )
        ),
    yaxis=dict(
        title='runtime/s',
        titlefont=dict(
            family='Courier New, monospace',
            size=18,
            color='#7f7f7f'
            )
        )
    )
}

plotly.offline.plot(plot_data)
