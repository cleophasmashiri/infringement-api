package com.zamaflow.bpm.api.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zamaflow.bpm.api.web.rest.TestUtil;

public class InfringementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infringement.class);
        Infringement infringement1 = new Infringement();
        infringement1.setId(1L);
        Infringement infringement2 = new Infringement();
        infringement2.setId(infringement1.getId());
        assertThat(infringement1).isEqualTo(infringement2);
        infringement2.setId(2L);
        assertThat(infringement1).isNotEqualTo(infringement2);
        infringement1.setId(null);
        assertThat(infringement1).isNotEqualTo(infringement2);
    }
}
