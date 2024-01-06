package edu.nuaa.lottery.interfaces.assembler;

import edu.nuaa.lottery.domain.strategy.model.vo.DrawAwardVO;
import edu.nuaa.lottery.rpc.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @Description 对象转换配置
 * @author brain
 * @version 1.0
 * @date 2024/1/5 10:50
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardVO, AwardDTO> {

    @Mapping(target = "userId", source = "uId")
    @Override
    AwardDTO sourceToTarget(DrawAwardVO var1);

    @Override
    DrawAwardVO targetToSource(AwardDTO var1);

}

