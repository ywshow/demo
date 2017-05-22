$(function() {
	$.get("../echarts/test", function(result) {
		var text='ECharts 入门示例';
		var legend = ['销量'];
		var tooltip ={};
		var xAxis = ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
		var yAxis = {};
		var series={name: '销量',type: 'bar',data: [5, 20, 36, 10, 10, 20]};
		initBarEcharts("main", text, tooltip, legend, xAxis, yAxis, series);
	});
});

//柱状图
function initBarEcharts(htmlId, text, tooltip, legend, xAxis, yAxis, series) {
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById(htmlId));
	// 指定图表的配置项和数据
	var option = {
		title : {
			text : text
		},
		tooltip : tooltip,
		legend : {
			data : legend
		},
		xAxis : {
			data : xAxis
		},
		yAxis : yAxis,
		series : [ series ]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}




/***********中国地图****************/
$(function() {
	function randomData() {
	    return Math.round(Math.random()*1000);
	}
	var myChart = echarts.init(document.getElementById("chinaMap"));
	var option = {
	    title: {
	        text: 'iphone销量',
	        subtext: '纯属虚构',
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item'
	    },
	    
	    visualMap: {
	        min: 0,
	        max: 2500,
	        left: 'left',
	        top: 'bottom',
	        text: ['高','低'],// 文本，默认为数值文本
	        calculable: true
	    },
	   
	    series: [
	        {
	            name: 'iphone3',
	            type: 'map',
	            mapType: 'china',
	            roam: false,
	            label: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            data:[
	                {name: '北京',value: randomData() },
	                {name: '天津',value: randomData() },
	                {name: '上海',value: randomData() },
	                {name: '重庆',value: randomData() },
	                {name: '河北',value: randomData() },
	                {name: '河南',value: randomData() },
	                {name: '云南',value: randomData() },
	                {name: '辽宁',value: randomData() },
	                {name: '黑龙江',value: randomData() },
	                {name: '湖南',value: randomData() },
	                {name: '安徽',value: randomData() },
	                {name: '山东',value: randomData() },
	                {name: '新疆',value: randomData() },
	                {name: '江苏',value: randomData() },
	                {name: '浙江',value: randomData() },
	                {name: '江西',value: randomData() },
	                {name: '湖北',value: randomData() },
	                {name: '广西',value: randomData() },
	                {name: '甘肃',value: randomData() },
	                {name: '山西',value: randomData() },
	                {name: '内蒙古',value: randomData() },
	                {name: '陕西',value: randomData() },
	                {name: '吉林',value: randomData() },
	                {name: '福建',value: randomData() },
	                {name: '贵州',value: randomData() },
	                {name: '广东',value: randomData() },
	                {name: '青海',value: randomData() },
	                {name: '西藏',value: randomData() },
	                {name: '四川',value: randomData() },
	                {name: '宁夏',value: randomData() },
	                {name: '海南',value: randomData() },
	                {name: '台湾',value: randomData() },
	                {name: '香港',value: randomData() },
	                {name: '澳门',value: randomData() }
	            ]
	        }
	        
	    ]
	};
	
	myChart.setOption(option);
});



