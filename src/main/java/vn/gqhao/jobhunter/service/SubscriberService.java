package vn.gqhao.jobhunter.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.gqhao.jobhunter.dto.request.SubscriberCreationRequest;
import vn.gqhao.jobhunter.dto.request.SubscriberUpdateRequest;
import vn.gqhao.jobhunter.dto.response.SubscriberResponse;
import vn.gqhao.jobhunter.entity.Skill;
import vn.gqhao.jobhunter.entity.Subscriber;
import vn.gqhao.jobhunter.exception.AppException;
import vn.gqhao.jobhunter.exception.ErrorCode;
import vn.gqhao.jobhunter.repository.SkillRepository;
import vn.gqhao.jobhunter.repository.SubscriberRepository;
import vn.gqhao.jobhunter.util.mapper.SubscriberMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriberService {
    SubscriberRepository subscriberRepository;
    SkillRepository skillRepository;
    SubscriberMapper subscriberMapper;

    @Transactional
    public SubscriberResponse handleCreateSubscriber(SubscriberCreationRequest request){
        if(subscriberRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.SUBSCRIBER_EXISTED);
        }
        List<Long> skillIdList = request.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toList());
        List<Skill> skillList = skillRepository.findByIdIn(skillIdList);
        if(skillList.isEmpty()){
            throw new AppException(ErrorCode.SUBSCRIBER_NOT_EXISTED);
        }
        Subscriber subscriber = Subscriber.builder()
                .email(request.getEmail())
                .name(request.getName())
                .skills(skillList)
                .build();
        subscriberRepository.save(subscriber);
        return subscriberMapper.SubscriberToSubscriberResponse(subscriber);
    }

    @Transactional
    public SubscriberResponse handleUpdateSubscriber(SubscriberUpdateRequest request){
        Subscriber subscriber = subscriberRepository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.SUBSCRIBER_NOT_EXISTED));
        List<Long> skillIdList = request.getSkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toList());
        List<Skill> skillList = skillRepository.findByIdIn(skillIdList);
        if(skillList.isEmpty()){
            throw new AppException(ErrorCode.SUBSCRIBER_NOT_EXISTED);
        }
        subscriber.setSkills(skillList);
        subscriberRepository.save(subscriber);
        return subscriberMapper.SubscriberToSubscriberResponse(subscriber);
    }
}
