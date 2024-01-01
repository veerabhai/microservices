package com.mbk.app.features.platform.data.model.experience.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
private String to;
private String subject;
private String body;
}