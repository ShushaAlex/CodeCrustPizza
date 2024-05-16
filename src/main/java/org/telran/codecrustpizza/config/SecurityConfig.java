package org.telran.codecrustpizza.config;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity // позволяет делать аннотации над методами в контроллере
public class SecurityConfig {

    //@Autowired
    //private final UserDetailService userDetailService;
//    @Bean
//    public PasswordEncoder passwordEncoder() { ипользуем одно из двух
//        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public AuthentificationManager authentificationManager(PasswordEncoder passwordEncoder)
//        DaoAuthentificationProvider daoAuthprovider = New DaoAuthentificationProvider();
//        daoAuthprovider.setUserDetailsService(userDetailsService);
//        daoAuthprovider.setPasswordEncoder(passwordEncoder);
//        return new ProviderManager(daoAuthprovider);
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthentificationManager authentificationManager) {
//        return http.csrf() // .disable(); чтобы отключить csrf
//                .disable()
//                .authorizeHttpRequest(authorize -> authorize
//                        .requestMatchers("/auth/login", "/auth/register", "/error").permitAll() //перечисляем открытые эндпойнты
//                        .anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .logout(Customizer.withDefaults())
//                .authentificationManager(authentificationManager)
//                .build();
//    }
}
