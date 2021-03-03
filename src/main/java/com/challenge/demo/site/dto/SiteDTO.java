package com.challenge.demo.site.dto;

import com.challenge.demo.site.Site;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SiteDTO {

    private final Long siteId;
    private final String siteUUID;
    private final String url;
    private final Date createdAt;
    private final Date updatedAt;

    @JsonCreator
    public SiteDTO(@JsonProperty("siteId") Long siteId,
                   @JsonProperty("siteUUID") String siteUUID,
                   @JsonProperty("url") String url,
                   @JsonProperty("createAt") Date createdAt,
                   @JsonProperty("updatedAt") Date updatedAt) {
        this.siteId = siteId;
        this.siteUUID = siteUUID;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Site createSite(UUID siteUUID) {
        return Site.builder()
                   .siteUUID(siteUUID)
                   .url(url)
                   .build();
    }

    public Site updateSite(Site site) {
        return site.toBuilder()
                   .url(url)
                   .build();
    }

    public static SiteDTO build(Site site) {
        return new SiteDTO(
                site.siteId(),
                site.siteUUID().toString(),
                site.url(),
                site.createdAt(),
                site.updatedAt()
        );
    }
}
