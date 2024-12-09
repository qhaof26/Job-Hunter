package vn.gqhao.jobhunter.util.mapper;

import org.springframework.stereotype.Component;
import vn.gqhao.jobhunter.dto.response.SubscriberResponse;
import vn.gqhao.jobhunter.entity.Subscriber;

@Component
public class SubscriberMapper {
    public SubscriberResponse SubscriberToSubscriberResponse(Subscriber subscriber){
        return SubscriberResponse.builder()
                .id(subscriber.getId())
                .email(subscriber.getEmail())
                .name(subscriber.getName())
                .skills(subscriber.getSkills())
                .build();
    }
}
