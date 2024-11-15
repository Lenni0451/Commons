package net.lenni0451.commons.asm.mappings.meta;

import lombok.Data;
import lombok.With;

import javax.annotation.Nonnull;
import java.util.List;

@Data
@With
public class MethodMetaMapping {

    @Nonnull
    private final String name;
    @Nonnull
    private final String descriptor;
    @Nonnull
    private final String[] javadoc;
    @Nonnull
    private final List<ParameterMetaMapping> parameters;

    public boolean hasJavadoc() {
        for (String line : this.javadoc) {
            if (!line.trim().isEmpty()) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.javadoc.length == 0;
    }

}
