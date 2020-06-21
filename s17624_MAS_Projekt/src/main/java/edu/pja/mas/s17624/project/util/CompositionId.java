package edu.pja.mas.s17624.project.util;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompositionId implements Serializable
{
    private long parentId;
    private long childId;

    public CompositionId()
    {

    }

    public CompositionId(long parentId, long childId)
    {
        this.parentId = parentId;
        this.childId = childId;
    }

    public long getParentId()
    {
        return parentId;
    }

    public long getChildId()
    {
        return childId;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
        {
            return true;
        }
        if(!(obj instanceof CompositionId))
        {
            return false;
        }
        CompositionId c = (CompositionId) obj;
        return (Objects.equals(this.getChildId(), c.getChildId()) &&
                Objects.equals(this.getParentId(), c.getParentId()));
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getChildId(), getParentId());
    }
}
