package com.planchella.mappers;

import com.planchella.domain.AttachmentMetadata;
import com.planchella.domain.Event;
import com.planchella.entities.AttachmentEntity;
import com.planchella.entities.CommunityEntity;
import com.planchella.entities.EventEntity;
import com.planchella.entities.UserEntity;
import com.planchella.enums.EventSize;
import com.planchella.enums.EventType;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EventMapperTest {

    @Test
    public void testDomainToEntitySetsBackReference() {
        // Arrange
        Session session = Mockito.mock(Session.class);
        UserEntity author = new UserEntity();
        author.setId(1L);
        CommunityEntity community = new CommunityEntity();
        community.setId(2L);

        when(session.getReference(eq(UserEntity.class), any())).thenReturn(author);
        when(session.getReference(eq(CommunityEntity.class), any())).thenReturn(community);
        when(session.getReference(eq(AttachmentEntity.class), eq("att1"))).thenReturn(new AttachmentEntity());
        when(session.getReference(eq(AttachmentEntity.class), eq("att2"))).thenReturn(new AttachmentEntity());

        List<AttachmentMetadata> attachments = new ArrayList<>();
        attachments.add(new AttachmentMetadata("att1", "file1.png", null, 100));
        attachments.add(new AttachmentMetadata("att2", "file2.png", null, 200));

        Event domainEvent = new Event(

        );

        // Act
        EventEntity entity = EventMapper.domainToEntity(domainEvent, session);

        // Assert
        assertNotNull(entity);
        assertEquals(10L, entity.getId());
        assertEquals(2, entity.getAttachments().size());

        for (AttachmentEntity attachmentEntity : entity.getAttachments()) {
            assertEquals(entity, attachmentEntity.getEvent(),
                    "Back-reference 'event' should be set to the parent EventEntity");
        }
    }
}
