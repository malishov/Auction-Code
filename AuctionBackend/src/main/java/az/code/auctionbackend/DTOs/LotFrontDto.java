package az.code.auctionbackend.DTOs;

import az.code.auctionbackend.entities.Lot;
import az.code.auctionbackend.entities.redis.RedisLot;
import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Builder
public class LotFrontDto {
    private long id;
    private String description;
    private String lotName;
    private double reservePrice;
    private double startingPrice;
    private double bidStep;
    private double currentBid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long userId;
    List<String> ids;
    List<BidDto> bids;
    private int status;
    private UserFrontDTO user;
    private UserFrontDTO winnerUser;
    private int type;

    public static LotFrontDto getLotFrontDto(RedisLot redisLot){

        System.out.println(redisLot);
        List<String> idsLocal = new ArrayList<>();

        if (redisLot.getImgs() != null){
        JSONObject obj = new JSONObject(redisLot.getImgs());
        Iterator<String> keys = obj.keys();

        while(keys.hasNext()) {
            String key = keys.next();
                idsLocal.add(obj.get(key).toString());
        }}

        LotFrontDto lot = LotFrontDto.builder()
                .id(redisLot.getId())
                .lotName(redisLot.getLotName())
                .description(redisLot.getDescription())
                .reservePrice(redisLot.getReservePrice())
                .startingPrice(redisLot.getStartingPrice())
                .bidStep(redisLot.getBidStep())
                .currentBid(redisLot.getCurrentBid())
                .startDate(redisLot.getStartDate())
                .endDate(redisLot.getEndDate())
                .ids(idsLocal)
                .userId(redisLot.getUserId())
                .bids(redisLot.getBidHistory())
                .status(1)
                .type(redisLot.getType())
                .build();

        return lot;
    }

    public static LotFrontDto getLotFrontDto(Lot lotIn){

        List<String> idsLocal = new ArrayList<>();

        if (lotIn.getItemPictures() != null){

        JSONObject obj = new JSONObject(lotIn.getItemPictures());
        Iterator<String> keys = obj.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            idsLocal.add(obj.get(key).toString());
        }}

        List<BidDto> bidsList = new ArrayList<>();

        lotIn.getBidHistory().forEach(b->{
            bidsList.add(BidDto.makeBidDto(b));
        });

        LotFrontDto lot = LotFrontDto.builder()
                .id(lotIn.getId())
                .lotName(lotIn.getLotName())
                .description(lotIn.getDescription())
                .reservePrice(lotIn.getReservePrice())
                .startingPrice(lotIn.getStartingPrice())
                .bidStep(lotIn.getBidStep())
                .currentBid(lotIn.getCurrentBid())
                .startDate(lotIn.getStartDate())
                .endDate(lotIn.getEndDate())
                .ids(idsLocal)
                .userId(lotIn.getUser().getId())
                .bids(bidsList)
                .status(lotIn.getStatus())
                .user(UserFrontDTO.convertToUserFront(lotIn.getUser()))
                .winnerUser(UserFrontDTO.convertToUserFront(lotIn.getLotWinner()))
                .type(lotIn.getType())
                .build();

        return lot;
    }

    public static LotFrontDto getLotFrontDtoWithoutBidsFirstImage(Lot lotIn){

        List<String> idsLocal = new ArrayList<>();

        if (lotIn.getItemPictures() != null){

            JSONObject obj = new JSONObject(lotIn.getItemPictures());

            idsLocal.add(obj.get("0").toString());
        }


        LotFrontDto lot = LotFrontDto.builder()
                .id(lotIn.getId())
                .lotName(lotIn.getLotName())
                .description(lotIn.getDescription())
                .reservePrice(lotIn.getReservePrice())
                .startingPrice(lotIn.getStartingPrice())
                .bidStep(lotIn.getBidStep())
                .currentBid(lotIn.getCurrentBid())
                .startDate(lotIn.getStartDate())
                .endDate(lotIn.getEndDate())
                .ids(idsLocal)
                .userId(lotIn.getUser().getId())
                .bids(null)
                .status(lotIn.getStatus())
                .user(UserFrontDTO.convertToUserFront(lotIn.getUser()))
                .winnerUser(UserFrontDTO.convertToUserFront(lotIn.getLotWinner()))
                .type(lotIn.getType())
                .build();

        return lot;
    }
}
