开发流程
向量的每个分量对应的函数全放在
lab.cgcl.aliBigdata.BPNNlearning.getVector
包下
继承 GetInfoFromDB ， 示例如Get00.java,
@lyk，为了不引起冲突，你的全放在
lab.cgcl.aliBigdata.BPNNlearning.getVector.brandRelation
下

命名规则 统一用GetXX ，从0开始
@important 
在bean里写好注释,告诉怎么用
需要输入什么样的参数要写明，我只预留了Object

在properties\spring.xml 里注册你的bean
格式照抄里面的

如
<bean id="get00" class="lab.cgcl.aliBigdata.BPNNlearning.getVector.Get00">  需要更改class值为你的类
    	<property name="dao">
	    <ref bean="dao" />
	   </property>
    </bean>

1.ID保持与类名一致 @lyk 你的类的ID自行加个前缀
2.需要更改class值为你的类
3.其他的属性不改。