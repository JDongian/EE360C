import plotly
from plotly.graph_objs import Scatter, Layout


data = ([
    7310,
    6221,
    909,
    901,
    876],
    [276696,
     277765,
     264433,
     270576,
     272380],
[196713,
234046,
247665,
242180,
271837,
242752])


data_g, data_dp, data_dh = data

x_s = [5,10,15,20,25]

plot_data = {
"data": [
    Scatter(x=x_s, y=data_g, name="GPSR"),
    Scatter(x=x_s, y=data_dp, name="Dijkstra Pair"),
    Scatter(x=x_s, y=data_dh, name="Dijkstra Hops"),
    ],
"layout": Layout(
    title="GPSR vs Dijkstra Pair Lat vs Dijkstra Hops",
    xaxis=dict(
        title='radius',
        titlefont=dict(
            family='Courier New, monospace',
            size=18,
            color='#7f7f7f'
            )
        ),
    yaxis=dict(
        title='runtime/ns',
        titlefont=dict(
            family='Courier New, monospace',
            size=18,
            color='#7f7f7f'
            )
        )
    )
}

plotly.offline.plot(plot_data)
