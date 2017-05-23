# ginkgo-scheduler
## ginkgo调度器
http://blog.csdn.net/loongshawn/article/details/50663393
<p>
定时任务实现方式：<br/>
Java自带的java.util.Timer类，这个类允许你调度一个java.util.TimerTask任务。
使用这种方式可以让你的程序按照某一个频度执行，但不能在指定时间运行。一般用的较少。<br/>
使用Quartz，这是一个功能比较强大的的调度器，可以让你的程序在指定时间执行，也可以按照某一个频度执行，
配置起来稍显复杂。<br/>
SpringBoot自带的Scheduled，可以将它看成一个轻量级的Quartz，而且使用起来比Quartz简单许多，本文主要介绍。
</p>
<p>
需要解决的问题<br/>
1、修改配置，不需要重启程序
</p>