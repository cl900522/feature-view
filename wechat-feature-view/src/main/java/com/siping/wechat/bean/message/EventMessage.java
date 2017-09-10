package com.siping.wechat.bean.message;

import java.util.Map;

public abstract class EventMessage extends Message {

    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    public static EventMessage create(Map<String, String> map) throws Exception {
        EventMessage message = null;
        String event = map.get("Event").toString();
        if((event.equals("subscribe") ||event.equals("unsubscribe")) && map.get("EventKey") == null){
            SubscribeEvent subscribeEvent = new SubscribeEvent();
            message = subscribeEvent;
        }else if (event.equals("subscribe") ||event.equals("SCAN")){
            QrEvent qrEvent = new QrEvent();
            qrEvent.setEventKey(map.get("EventKey").toString());
            qrEvent.setTicket(map.get("Ticket").toString());
            message = qrEvent;
        }else if (event.equals("LOCATION")){
            LocationEvent locationEvent = new LocationEvent();
            locationEvent.setLatitude(map.get("Latitude").toString());
            locationEvent.setLongitude(map.get("Longitude").toString());
            locationEvent.setPrecision(map.get("Precision").toString());
            message = locationEvent;
        }else if (event.equals("CLICK")){
            MenuEvent mentEvent = new MenuEvent();
            mentEvent.setEventKey(map.get("EventKey").toString());
            message = mentEvent;
        }
        else if (event.equals("VIEW")){
            MenuEvent mentEvent = new MenuEvent();
            mentEvent.setEventKey(map.get("EventKey").toString());
            message = mentEvent;
        }
        message.setEvent(map.get("Event").toString());
        message.setToUserName(map.get("ToUserName").toString());
        message.setFromUserName(map.get("FromUserName").toString());
        message.setCreateTime(Long.parseLong(map.get("CreateTime").toString()));
        message.setMsgType(MessageType.create(map.get("MsgType").toString()));
        return message;
    }
}
