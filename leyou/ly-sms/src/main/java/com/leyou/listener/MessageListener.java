package com.leyou.listener;

import com.leyou.utils.SmsUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageListener {

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name="sms.send.queue",durable = "true"),
            exchange = @Exchange(name="sms.changes",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key=("sms.send")
    ))
    public void sendMessage(Map<String,String> map){
        if(map.size()>0&& map!=null){
            SmsUtils.sendMsg(map.get("phone"),map.get("code"));
        }
    }
}
